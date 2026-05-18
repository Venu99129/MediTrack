package org.airtribe.Services;

import org.airtribe.entity.BillSummary;
import org.airtribe.repository.BillRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BillServiceTest {

    @Test
    public void saveBill_callsRepository() throws Exception {
        BillRepository repo = new BillRepository() {
            @Override
            public void saveBill(BillSummary billSummary) {
                // do nothing
            }
        };

        BillService billService = new BillService(repo);
        BillSummary bs = new BillSummary(1,1,1,100.0);
        billService.saveBill(bs);
        // no exception means pass
    }
}
