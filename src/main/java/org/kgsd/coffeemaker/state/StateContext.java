package org.kgsd.coffeemaker.state;

/**
 *  StateContext can be implemented if there are more states
 *  As we have only 2 states, success and failure, we are switching
 *  on the states for brevity
 */
public class StateContext {
    private Integer outlets;
    private Integer servedBeverages;

    public Integer getOutlets() {
        return outlets;
    }

    public void setOutlets(Integer outlets) {
        this.outlets = outlets;
    }

    public Integer getServedBeverages() {
        return servedBeverages;
    }

    public void setServedBeverages(Integer servedBeverages) {
        this.servedBeverages = servedBeverages;
    }
}
