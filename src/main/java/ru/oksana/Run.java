package ru.oksana;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.oksana.model.Supplier;
import ru.oksana.repository.SupplierRepo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@SpringBootApplication
public class Run implements ApplicationRunner {

    private final SupplierRepo supplierRepo;

    public Run(SupplierRepo supplierRepo) {
        this.supplierRepo = supplierRepo;
    }

    @Override
    public void run(ApplicationArguments args) {
        addInitData();
    }

    private void addInitData() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(
                                new File("suppliers.txt")
                        )
                )
        )) {
            String line;
            while ((line = br.readLine()) != null) {
                supplierRepo.save(parseLine(line.split(";")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Supplier parseLine(String[] split) {
        System.out.println(Arrays.toString(split));
        return new Supplier(String.valueOf(split[0]), String.valueOf(split[1]), Double.valueOf(split[2]),
                Double.valueOf(split[3]), Integer.valueOf(split[4]), Integer.valueOf(split[5]), Double.valueOf(split[6]));
    }

}
