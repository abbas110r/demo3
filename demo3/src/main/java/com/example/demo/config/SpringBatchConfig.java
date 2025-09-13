package com.example.demo.config;

import com.example.demo.entity.Sales;
import com.example.demo.repository.SalesRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    JobRepository jobRepository;

    SalesRepository salesRepository;

    PlatformTransactionManager platformTransactionManager;

    public SpringBatchConfig(JobRepository jobRepository, SalesRepository salesRepository, PlatformTransactionManager platformTransactionManager) {
        this.jobRepository = jobRepository;
        this.salesRepository = salesRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean
    public Job job(){
        return new JobBuilder("importSalesData Job",jobRepository).start(step()).build();
    }

    @Bean
    public Step step(){
        return new StepBuilder("importSales Data",jobRepository).<Sales,Sales>chunk(10,platformTransactionManager).reader(fileItemReader()).writer(repositoryItemWriter()).build();
    }

    @Bean
    public FlatFileItemReader<Sales>fileItemReader(){
        return new FlatFileItemReaderBuilder<Sales>().resource(new ClassPathResource("sales.csv")).linesToSkip(2).name("salesDataReader").lineMapper(lineMapper()).build();
    }

    @Bean
    public RepositoryItemWriter<Sales>repositoryItemWriter(){
        return new RepositoryItemWriterBuilder<Sales>().repository(salesRepository).methodName("save").build();
    }

    private LineMapper<Sales>lineMapper(){
        DefaultLineMapper<Sales>lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setQuoteCharacter('"');
        lineTokenizer.setStrict(true);
        lineTokenizer.setNames("saleID", "date", "customerID", "customerName", "customerContact", "productID", "productName", "quantity", "unitPrice", "totalPrice", "subTotal", "tax", "totalAmount", "paymentMethod", "salesRep", "deptID", "deptName", "storeNumber");
        BeanWrapperFieldSetMapper<Sales>fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Sales.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

}
