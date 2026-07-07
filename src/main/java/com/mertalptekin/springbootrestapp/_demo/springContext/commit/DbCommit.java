package com.mertalptekin.springbootrestapp._demo.springContext.commit;

import org.springframework.stereotype.Component;

@Component("dbCommit") // Bean ismi dbCommit olarak tanımlandı
public class DbCommit  implements ICommit {
    @Override
    public void commitChanges() {
        System.out.println("Db Commit changes");
    }
}
