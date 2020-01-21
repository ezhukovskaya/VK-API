package framework.base;

import framework.elements.Label;

public abstract class BaseForm {
    private Label label;

    public BaseForm(Label label) {
        this.label = label;
    }

    public boolean isPageDisplayed() {
        return this.label.isDisplayed();
    }
}