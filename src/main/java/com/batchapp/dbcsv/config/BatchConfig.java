package com.batchapp.dbcsv.config;

import com.batchapp.dbcsv.entity.DataUser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchConfig {

    @Bean
    public JpaPagingItemReader<DataUser> reader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<DataUser>()
                .name("userReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT u FROM DataUser u")
                .pageSize(1)
                .build();
    }

    @Bean
    public FlatFileItemWriter<DataUser> writer() {
        BeanWrapperFieldExtractor<DataUser> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[]{"id", "name", "email"});

        DelimitedLineAggregator<DataUser> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);

        return new FlatFileItemWriterBuilder<DataUser>()
                .name("userWriter")
                .resource(new FileSystemResource("users.csv"))
                .lineAggregator(lineAggregator)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager transactionManager,
                     JpaPagingItemReader<DataUser> reader,
                     FlatFileItemWriter<DataUser> writer, DataUserProcessor dataUserProcessor) {
        return new StepBuilder("step", jobRepository)
                .<DataUser, DataUser>chunk(2, transactionManager)
                .reader(reader)
                .writer(writer)
                .processor(dataUserProcessor)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job exportUserJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("exportUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
}

