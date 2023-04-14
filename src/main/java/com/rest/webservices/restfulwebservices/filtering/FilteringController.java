package com.rest.webservices.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
        PropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;

    }
    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {
        ArrayList<SomeBean> someBean = new ArrayList<>();
        someBean.add(new SomeBean("field1","field2","field3"));
        someBean.add(new SomeBean("field4","field5","field6"));
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
        PropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1");
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);
        mappingJacksonValue.setFilters(filters);


        return  mappingJacksonValue;
        } ;

    }

