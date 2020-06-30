package org.kgsd.coffeemaker.dao;

import com.google.inject.AbstractModule;
import org.kgsd.coffeemaker.dao.impl.FileInventoryDao;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(InventoryDao.class).to(FileInventoryDao.class).asEagerSingleton();
    }
}
