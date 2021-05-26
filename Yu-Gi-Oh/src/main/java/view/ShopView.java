package view;

import controller.ShopController;

public class ShopView extends AbstractView {

    private final ShopController controller;

    public ShopView(ShopController controller) {
        this.controller = controller;
    }

    @Override
    protected boolean runCommand(String input) {
        return true;
    }
}
