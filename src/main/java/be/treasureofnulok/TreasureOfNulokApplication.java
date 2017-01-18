package be.treasureofnulok;

import be.treasureofnulok.Models.Item;
import be.treasureofnulok.Models.Player;
import be.treasureofnulok.Receivers.ItemReceiver;
import be.treasureofnulok.Repositories.ItemRepository;
import be.treasureofnulok.Repositories.PlayerRepository;
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
	public CommandLineRunner demo(ItemRepository itemRepository, PlayerRepository playerRepository, ItemReceiver receiver, RabbitTemplate template, ConfigurableApplicationContext context) {
		return (args) -> {
			// save a couple of items
			itemRepository.save(new Item("small hp potion","common"));
			itemRepository.save(new Item("medium hp potion","uncommon"));
			itemRepository.save(new Item("greater hp potion","rare"));
			itemRepository.save(new Item("large hp potion","epic"));

			playerRepository.save(new Player("Tristan","male", "human").addItem(new Item("knife","common")));
			playerRepository.save(new Player("Alyn","female", "elf").addItem(new Item("staff","common")));

			// fetch all items
			log.info("Items found with findAll():");
			log.info("-------------------------------");
			for (Item item : itemRepository.findAll()) {
				log.info(item.toString());
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
			Item item = itemRepository.findOne(1L);
			log.info("Item found with findOne(1L):");
			log.info("--------------------------------");
			log.info(item.toString());
			log.info("");

			// fetch item by name
			log.info("Item found with findByNameIgnoringCase('small hp potion'):");
			log.info("--------------------------------------------");
			for(Item smallHpPotion :itemRepository.findByNameIgnoringCase("small hp potion")) {
				log.info(smallHpPotion.toString());
				System.out.println("Sending item...");
				template.convertAndSend(QUEUE_NAME, smallHpPotion);
				receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
				context.close();
			}

			log.info("");


		};
	}
}
