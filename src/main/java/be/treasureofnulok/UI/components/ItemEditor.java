package be.treasureofnulok.UI.components;

import be.treasureofnulok.Models.Item;
import be.treasureofnulok.Receivers.ItemReceiver;
import be.treasureofnulok.Repositories.ItemRepository;
import be.treasureofnulok.Services.ItemService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
@SpringComponent
@UIScope
public class ItemEditor extends VerticalLayout {
    private final ItemService service;

    /**
     * The currently edited item
     */
    private Item item;

    /* Fields to edit properties in Customer entity */
    TextField name = new TextField("Name");
    TextField rarity = new TextField("Rarity");

    /* Action buttons */
    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public ItemEditor(ItemService service, ItemReceiver receiver) {
        this.service = service;

        addComponents(name, rarity, actions);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> service.saveItem(item));
        delete.addClickListener(e -> service.deleteItem(item));
        cancel.addClickListener(e -> editCustomer(item));
        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editCustomer(Item i) {
        final boolean persisted = i.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            item = service.findItemById(i.getId());
        }
        else {
            item = i;
        }
        cancel.setVisible(persisted);

        // Bind item properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        BeanFieldGroup.bindFieldsUnbuffered(item, this);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        name.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}
