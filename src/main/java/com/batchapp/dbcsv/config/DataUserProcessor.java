package com.batchapp.dbcsv.config;

import com.batchapp.dbcsv.entity.DataUser;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DataUserProcessor implements ItemProcessor<DataUser, DataUser> {

    public static final String MODIFIED = "_modified";

    @Override
    public DataUser process(DataUser dataUser) throws Exception {
        if(Objects.isNull(dataUser)) return null;
        DataUser dataUserReturn = new DataUser();
        dataUserReturn.setEmail(dataUser.getEmail() + MODIFIED);
        dataUserReturn.setId(dataUser.getId());
        dataUserReturn.setName(dataUser.getName()+ MODIFIED);
        return dataUserReturn;
    }
}
