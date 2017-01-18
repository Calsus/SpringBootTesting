package be.treasureofnulok.UI;

import be.treasureofnulok.Models.Item;
import be.treasureofnulok.Repositories.ItemRepository;
import be.treasureofnulok.UI.components.ItemEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Created by Jerry-Lee on 18/01/2017.
 */
@SpringUI
@Theme("valo")
public class VaadinUI extends UI {
    private final ItemRepository repo;
    private final ItemEditor editor;
    final Grid grid;
    final TextField filter;
    private final Button addNewBtn;
    private static final Logger log = LoggerFactory.getLogger(VaadinUI.class);
    @Autowired
    public VaadinUI(ItemRepository repo, ItemEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid();
        this.filter = new TextField();
        this.addNewBtn = new Button("New item", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
        setContent(mainLayout);

        // Configure layouts and components
        actions.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        grid.setHeight(300, Unit.PIXELS);
        grid.setColumns("id", "name", "rarity");

        filter.setInputPrompt("Filter by name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.addTextChangeListener(e -> listItems(e.getText()));

        // Connect selected Customer to editor or hide if none is selected
        grid.addSelectionListener(e -> {
            if (e.getSelected().isEmpty()) {
                editor.setVisible(false);
            }
            else {
                editor.editCustomer((Item) grid.getSelectedRow());
            }
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editCustomer(new Item("", "")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listItems(filter.getValue());
        });

        // Initialize listing
        listItems(null);
    }

    private void listItems(String text) {
        if(StringUtils.isEmpty(text)){
            grid.setContainerDataSource(new BeanItemContainer(Item.class,repo.findAll()));
        }
        else {
            grid.setContainerDataSource(new BeanItemContainer(Item.class,repo.findByNameIgnoringCase(text)));
        }
    }

}
