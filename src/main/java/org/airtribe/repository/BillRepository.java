package org.airtribe.repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.airtribe.entity.BillSummary;
import org.airtribe.exceptions.CSVFilesException;
import org.airtribe.exceptions.ContentNotFoundException;
import org.airtribe.util.CSVUtil;

import java.util.List;

public class BillRepository {


     // Saves a bill summary record to the bills CSV file.
     public void saveBill(BillSummary billSummary) throws CSVFilesException {

         String[] billData = {String.valueOf(billSummary.getId()), String.valueOf(billSummary.getPatientId()),String.valueOf(billSummary.getAppointmentId()), String.valueOf(billSummary.getTotalAmount())};

         try(CSVWriter billWriter = CSVUtil.getBillWriter()) {
             billWriter.writeNext(billData);
             billWriter.flush();
         } catch (Exception e) {
             throw new CSVFilesException(e.getMessage(), e.getStackTrace());
         }
     }

     // Retrieves all bill summaries for the specified patient ID from the bills CSV.
     public List<BillSummary> getBillsByPatientId(int patientId) throws CSVFilesException {
         try (CSVReader billReader = CSVUtil.getBillReader()) {
             List<String[]> billDataList = billReader.readAll();
             billDataList.removeFirst(); // Remove header row if present

             if(billDataList.isEmpty()) {
                 throw new ContentNotFoundException("No bill data found for patient ID: "+patientId);
             }

             return billDataList.stream()
                     .filter(data -> Integer.parseInt(data[1]) == patientId) // Ensure there are enough columns
                     .map(data -> new BillSummary(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]) , Double.parseDouble(data[3])))
                     .toList();
         } catch (Exception e) {
             throw new CSVFilesException(e.getMessage(), e.getStackTrace());
         }
     }
}
