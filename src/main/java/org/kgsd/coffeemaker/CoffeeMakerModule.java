package org.kgsd.coffeemaker;

import com.google.inject.AbstractModule;
import org.kgsd.coffeemaker.dao.DaoModule;
import org.kgsd.coffeemaker.service.DispenserService;
import org.kgsd.coffeemaker.state.StateContext;

public class CoffeeMakerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DispenserService.class).asEagerSingleton();
        bind(StateContext.class).asEagerSingleton();
        install(new DaoModule());
    }
}
