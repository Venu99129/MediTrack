package org.airtribe.Services;

import org.airtribe.entity.BillSummary;
import org.airtribe.exceptions.CSVFilesException;
import org.airtribe.exceptions.ContentNotFoundException;
import org.airtribe.repository.BillRepository;

import java.util.List;

public class BillService {

    BillRepository billRepository;

    // Default constructor that initializes the BillRepository.
    public BillService() {
        this.billRepository = new BillRepository();
    }

    // Injectable constructor used for unit tests to supply a mock repository.
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    // Saves a BillSummary via the repository, swallowing exceptions for top-level callers.
    public void saveBill(BillSummary billSummary) {
        try {
            billRepository.saveBill(billSummary);

        } catch (Exception e) {
            System.out.println("Error saving bill data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Retrieves bill summaries for a patient by delegating to the repository.
    public List<BillSummary> getBillsByPatientId(int patientId) throws CSVFilesException, ContentNotFoundException {
        try {
            return billRepository.getBillsByPatientId(patientId);
        } catch  (CSVFilesException e) {
           throw new CSVFilesException(e.getMessage(), e.getStackTrace());
        }catch (Exception e){
            throw new ContentNotFoundException(e.getMessage());
        }
    }


}
