package be.treasureofnulok;

import be.treasureofnulok.Models.*;
import be.treasureofnulok.Receivers.ItemReceiver;
import be.treasureofnulok.Repositories.*;
import be.treasureofnulok.Services.ItemService;
import be.treasureofnulok.Services.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class TreasureOfNulokApplication {
	private static final String QUEUE_NAME = "item";
	private static final Logger log = LoggerFactory.getLogger(TreasureOfNulokApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TreasureOfNulokApplication.class, args);
	}

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME,false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange("item-exchange");
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_NAME);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(ItemReceiver receiver) {
		return new MessageListenerAdapter(receiver,"receiveItem");
	}

	@Bean
	public CommandLineRunner demo(ItemRepository itemRepository, PlayerRepository playerRepository,
								  RaceRepository raceRepository, StorageRepository storageRepository,
								  PlayerItemRepository playerItemRepository, ItemReceiver receiver,
								  RabbitTemplate template, ItemService itemService,
								  PlayerService playerService, ConfigurableApplicationContext context) {
		return (args) -> {

			// save a couple of storages
			storageRepository.save(new Storage("Inventory", 16));
			storageRepository.save(new Storage("Bank", 36));

			// save a couple of races
			raceRepository.save(new Race("Human"));
			raceRepository.save(new Race("Elf"));
			raceRepository.save(new Race("Orc"));

			// save a couple of players
			playerRepository.save(new Player("Tristan","male", raceRepository.findByNameIgnoringCase("Human")));
			playerRepository.save(new Player("Alyn","female", raceRepository.findByNameIgnoringCase("Elf")));
			playerRepository.save(new Player("John","male", raceRepository.findByNameIgnoringCase("Elf")));
			playerRepository.save(new Player("Caitlyn","female", raceRepository.findByNameIgnoringCase("Orc")));

			// save a couple of items
			itemRepository.save(new Item("small hp potion", Rarity.COMMON));
			itemRepository.save(new Item("medium hp potion",Rarity.UNCOMMON));
			itemRepository.save(new Item("greater hp potion",Rarity.RARE));
			itemRepository.save(new Item("large hp potion",Rarity.EPIC));
			itemRepository.save(new Item("sword",Rarity.COMMON));
			itemRepository.save(new Item("staff",Rarity.COMMON));
			itemRepository.save(new Item("bow",Rarity.COMMON));

			itemService.storeItemInPlayerInventory(playerRepository.findByNameIgnoringCase("Alyn"),
					itemRepository.findItemByNameIgnoringCase("staff"),1);
			itemService.storeItemInPlayerInventory(playerRepository.findByNameIgnoringCase("Tristan"),
					itemRepository.findItemByNameIgnoringCase("sword"),1);
			itemService.storeItemInPlayerInventory(playerRepository.findByNameIgnoringCase("John"),
					itemRepository.findItemByNameIgnoringCase("bow"),1);
			itemService.storeItemInPlayerInventory(playerRepository.findByNameIgnoringCase("Caitlyn"),
					itemRepository.findItemByNameIgnoringCase("staff"),1);

			// fetch all items
			log.info("Items found with findAll():");
			log.info("-------------------------------");
			for (Item item : itemRepository.findAll()) {
				log.info(item.toString());
			}
			log.info("");

			// fetch all races
			log.info("Races found with findAll():");
			log.info("-------------------------------");
			for (Race race : raceRepository.findAll()) {
				log.info(race.toString());
			}
			log.info("");

			// fetch all storages
			log.info("Storages found with findAll():");
			log.info("-------------------------------");
			for (Storage storage : storageRepository.findAll()) {
				log.info(storage.toString());
			}
			log.info("");

			// fetch all items
			log.info("PlayerItems found with findAll():");
			log.info("-------------------------------");
			for (PlayerItem playerItem : playerItemRepository.findAll()) {
				log.info(playerItem.toString());
			}
			log.info("");

			// fetch all players
			log.info("Players found with findAll():");
			log.info("-------------------------------");
			for (Player player : playerRepository.findAll()) {
				log.info(player.toString());
			}
			log.info("");

			// fetch an individual item by ID
			Item item1 = itemRepository.findOne(1L);
			log.info("Item found with findOne(1L):");
			log.info("--------------------------------");
			log.info(item1.toString());
			log.info("");

			// fetch item by name
			log.info("Item found with findByNameIgnoringCase('small hp potion'):");
			log.info("--------------------------------------------");
			Item smallHpPotion = itemRepository.findItemByNameIgnoringCase("small hp potion");
			log.info(smallHpPotion.toString());
			log.info("");

			System.out.println("Sending item...");
			template.convertAndSend(QUEUE_NAME, smallHpPotion);
			receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
			log.info("");

			// fetch race by name
			log.info("Race found with findByNameIgnoringCase('human'):");
			log.info("--------------------------------------------");
			Race human = raceRepository.findByNameIgnoringCase("human");
			log.info(human.toString());
			log.info("");

			// fetch player by name
			log.info("Player found with findByNameIgnoringCase('Alyn'):");
			log.info("--------------------------------------------");
			Player alyn = playerRepository.findByNameIgnoringCase("Alyn");
			log.info(alyn.toString());
			log.info("");

			// fetch players by race
			log.info("Players found of Race Elf:");
			log.info("--------------------------------------------");
			for(Player player : playerRepository.findPlayersByRace(raceRepository.findByNameIgnoringCase("elf"))) {
				log.info(player.toString());
			}

			// fetch players by race
			log.info("Items found of Player Alyn:");
			log.info("--------------------------------------------");
			for(Item item : playerService.findItemsOfPlayer(playerRepository.findByNameIgnoringCase("Alyn"))) {
				log.info(item.toString());
			}

			log.info("");

			context.close();

		};
	}
}
