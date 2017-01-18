package be.treasureofnulok.Receivers;

import be.treasureofnulok.Models.Item;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
@Component
public class ItemReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }
    public void receiveItem(Item item) {
        System.out.println(item.toString());

    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
