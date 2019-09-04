package org.liquidengine.legui.demo.layout;

import org.joml.Vector2f;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.demo.Demo;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.event.MouseClickEvent.MouseClickAction;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.Style.DisplayType;
import org.liquidengine.legui.style.Style.PositionType;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.flex.FlexStyle.*;
import org.liquidengine.legui.style.util.StyleUtilities;
import org.liquidengine.legui.theme.Themes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.CLICK;
import static org.liquidengine.legui.event.MouseClickEvent.MouseClickAction.RELEASE;

/**
 * @author Aliaksandr_Shcherbin.
 */
public class MenuLayerDemo extends Demo {

    public static final float MENU_HEIGHT = 20;
    private Frame frame;

    public MenuLayerDemo(int width, int height, String title) {
        super(width, height, title);
    }

    public static void main(String[] args) {
        Demo demo = new MenuLayerDemo(600, 400, "Menu Layer Demo");
        demo.run();
    }

    @Override
    protected void update() {

    }

    @Override
    protected void createGuiElements(Frame frame, int width, int height) {
        this.frame = frame;
        MenuContainer menuContainer = new MenuContainer();
        MenuBar menuBar = menuContainer.getMenuBar();

        MenuBarItem menuBarItem = new MenuBarItem("File");
        menuBarItem.getStyle().setWidth(60f);
        menuBarItem.getStyle().setMinWidth(60f);
        menuBarItem.getStyle().setMaxWidth(60f);
        menuBar.addMenu(menuBarItem);

        MenuBarItemOption exitOption = new MenuBarItemOption("Exit");
        exitOption.getStyle().setWidth(60f);
        exitOption.getStyle().setMinWidth(60f);
        exitOption.getStyle().setMaxWidth(60f);
        exitOption.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
            if (CLICK == event.getAction()) {
                stop();
            }
        });
        menuBarItem.addMenuBarItemOption(exitOption);

        MenuBarItem about = new MenuBarItem("About");
        about.getStyle().setWidth(60f);
        about.getStyle().setMinWidth(60f);
        about.getStyle().setMaxWidth(60f);
        menuBar.addMenu(about);

        frame.getComponentLayer().setContainer(menuContainer);
    }

    private class MenuContainer extends LayerContainer {

        private MenuBar menuBar;
        private Panel container;

        public MenuContainer() {
            initialize();
        }

        private void initialize() {
            menuBar = new MenuBar();

            container = new Panel();
            container.getStyle().getFlexStyle().setAlignSelf(AlignSelf.STRETCH);

            container.getStyle().getFlexStyle().setFlexGrow(1);
            container.getStyle().getFlexStyle().setFlexShrink(1);

            container.getStyle().setPosition(PositionType.RELATIVE);

            container.getStyle().getBackground().setColor(ColorConstants.lightRed());

            this.add(menuBar);
            this.add(container);

            this.getStyle().setDisplay(DisplayType.FLEX);
            this.getStyle().setPadding(0f);
            this.getStyle().getFlexStyle().setFlexDirection(FlexDirection.COLUMN);

            this.getStyle().getFlexStyle().setAlignSelf(AlignSelf.AUTO);
            this.getStyle().getFlexStyle().setAlignItems(AlignItems.FLEX_START);
            this.getStyle().getFlexStyle().setJustifyContent(JustifyContent.FLEX_START);
            this.getStyle().getFlexStyle().setAlignContent(AlignContent.STRETCH);
        }

        public MenuBar getMenuBar() {
            return menuBar;
        }

        public Panel getContainer() {
            return container;
        }
    }

    private class MenuBar extends Panel {

        private List<MenuBarItem> menuBarItems;

        public MenuBar() {
            initialize();
        }

        private void initialize() {
            menuBarItems = new CopyOnWriteArrayList<>();

            Themes.getDefaultTheme().getThemeManager().getComponentTheme(Panel.class).apply(this);

            this.getStyle().setHeight(MENU_HEIGHT);
            this.getStyle().setMinHeight(MENU_HEIGHT);
            this.getStyle().setMaxHeight(MENU_HEIGHT);

            this.getStyle().setLeft(0f);
            this.getStyle().setTop(0f);
            this.getStyle().setRight(0f);
            this.getStyle().setPosition(PositionType.RELATIVE);

            this.getStyle().setDisplay(DisplayType.FLEX);
            this.getStyle().getFlexStyle().setFlexDirection(FlexDirection.ROW);

            this.getStyle().getFlexStyle().setAlignSelf(AlignSelf.STRETCH);
            this.getStyle().getFlexStyle().setAlignItems(AlignItems.STRETCH);
            this.getStyle().getFlexStyle().setJustifyContent(JustifyContent.FLEX_START);
            this.getStyle().getFlexStyle().setAlignContent(AlignContent.STRETCH);
        }

        public void addMenu(MenuBarItem menuBarItem) {
            if (add(menuBarItem)) {
                menuBarItems.add(menuBarItem);
            }
        }

        public List<MenuBarItem> getMenuBarItems() {
            return menuBarItems;
        }
    }

    private class MenuBarItem extends ToggleButton {

        private Layer<MenuBarItemOptionPanel> layer;
        private MenuBarItemOptionPanel panel;

        public MenuBarItem(String text) {
            super(text);
            initialize();
        }

        public void addMenuBarItemOption(MenuBarItemOption menuBarItemOption) {
            panel.addMenuBarItemOption(menuBarItemOption);
        }

        private void initialize() {
            layer = new Layer<>();
            panel = new MenuBarItemOptionPanel(layer, this);

            layer.setEventPassable(true);
            layer.setEventReceivable(true);

            layer.add(panel);
            layer.getContainer().getStyle().getBackground().setColor(ColorConstants.red().div(4f));
            layer.getContainer().setFocusable(true);
            layer.getContainer().getListenerMap().addListener(MouseClickEvent.class, event -> {
                event.getFrame().removeLayer(layer);
                this.setToggled(false);
            });

            Themes.getDefaultTheme().getThemeManager().getComponentTheme(ToggleButton.class).apply(this);

            this.getStyle().setHeight(MENU_HEIGHT);
            this.getStyle().setMinHeight(MENU_HEIGHT);
            this.getStyle().setMaxHeight(MENU_HEIGHT);

            this.getStyle().setPosition(PositionType.RELATIVE);

            this.getStyle().getFlexStyle().setFlexGrow(0);
            this.getStyle().getFlexStyle().setFlexShrink(0);

            this.getStyle().getFlexStyle().setAlignSelf(AlignSelf.AUTO);
            this.getStyle().getFlexStyle().setAlignItems(AlignItems.STRETCH);
            this.getStyle().getFlexStyle().setJustifyContent(JustifyContent.FLEX_START);
            this.getStyle().getFlexStyle().setAlignContent(AlignContent.STRETCH);

            this.getStyle().getBackground().setColor(ColorConstants.lightGray());
            this.setToggledBackgroundColor(ColorConstants.lightGray().sub(.1f, .1f, .1f, 0f));

            this.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
                if (MouseClickAction.CLICK.equals(event.getAction())) {
                    MenuBarItem menuBarItem = (MenuBarItem) event.getTargetComponent();
                    MenuBar menuBar = (MenuBar) menuBarItem.getParent();
                    for (MenuBarItem item : menuBar.getMenuBarItems()) {
                        if (item != menuBarItem) {
                            item.setToggled(false);
                        }
                    }

                    if (menuBarItem.isToggled()) {
                        // show menu bar panel
                        Vector2f size = event.getFrame().getContainer().getSize();
                        layer.setSize(size.x, size.y - MENU_HEIGHT);
                        layer.getContainer().setPosition(0, MENU_HEIGHT);
                        event.getFrame().addLayer(layer);
                    }
                }
            });
        }
    }

    private class MenuBarItemOptionPanel extends Panel {

        private List<MenuBarItemOption> menuBarItemOptions;
        private Layer<MenuBarItemOptionPanel> layer;
        private MenuBarItem menuBarItem;

        public MenuBarItemOptionPanel(Layer<MenuBarItemOptionPanel> layer, MenuBarItem menuBarItem) {
            this.layer = layer;
            this.menuBarItem = menuBarItem;
            initialize();
        }

        private void initialize() {
            setSize(0, 0);
            menuBarItemOptions = new CopyOnWriteArrayList<>();

            this.getStyle().setDisplay(DisplayType.FLEX);
            this.getStyle().setPadding(0f);
            this.getStyle().getFlexStyle().setFlexDirection(FlexDirection.COLUMN);

            this.getStyle().getFlexStyle().setAlignSelf(AlignSelf.AUTO);
            this.getStyle().getFlexStyle().setAlignItems(AlignItems.FLEX_START);
            this.getStyle().getFlexStyle().setJustifyContent(JustifyContent.FLEX_START);
            this.getStyle().getFlexStyle().setAlignContent(AlignContent.STRETCH);
        }

        public void addMenuBarItemOption(MenuBarItemOption menuBarItemOption) {
            if (this.add(menuBarItemOption)) {
                this.getSize().x = Math.max(this.getSize().x,
                    StyleUtilities.getFloatLengthNullSafe(menuBarItemOption.getStyle().getWidth(), menuBarItemOption.getParent().getSize().x));
                this.getSize().y = this.getSize().y + MENU_HEIGHT;
                menuBarItemOptions.add(menuBarItemOption);
                menuBarItemOption.setLayer(layer);
                menuBarItemOption.setMenuBarItem(menuBarItem);
            }
        }

        public List<MenuBarItemOption> getMenuBarItemOptions() {
            return menuBarItemOptions;
        }
    }

    private class MenuBarItemOption extends Button {

        private Layer<MenuBarItemOptionPanel> layer;
        private MenuBarItem menuBarItem;

        public MenuBarItemOption(String text) {
            super(text);
            initialize();
        }

        private void initialize() {
            Themes.getDefaultTheme().getThemeManager().getComponentTheme(Button.class).apply(this);

            this.getStyle().setHeight(MENU_HEIGHT);
            this.getStyle().setMinHeight(MENU_HEIGHT);
            this.getStyle().setMaxHeight(MENU_HEIGHT);

            this.getStyle().setPosition(PositionType.RELATIVE);

            this.getStyle().getFlexStyle().setFlexGrow(0);
            this.getStyle().getFlexStyle().setFlexShrink(0);

            this.getStyle().getFlexStyle().setAlignSelf(AlignSelf.AUTO);
            this.getStyle().getFlexStyle().setAlignItems(AlignItems.STRETCH);
            this.getStyle().getFlexStyle().setJustifyContent(JustifyContent.FLEX_START);
            this.getStyle().getFlexStyle().setAlignContent(AlignContent.STRETCH);

            this.getStyle().getBackground().setColor(ColorConstants.lightGray());

            this.getListenerMap().addListener(MouseClickEvent.class, (MouseClickEventListener) event -> {
                if (RELEASE.equals(event.getAction())) {
                    event.getFrame().removeLayer(layer);
                    menuBarItem.setToggled(false);
                }
            });
        }

        public Layer<MenuBarItemOptionPanel> getLayer() {
            return layer;
        }

        public void setLayer(Layer<MenuBarItemOptionPanel> layer) {
            this.layer = layer;
        }

        public MenuBarItem getMenuBarItem() {
            return menuBarItem;
        }

        public void setMenuBarItem(MenuBarItem menuBarItem) {
            this.menuBarItem = menuBarItem;
        }
    }
}
