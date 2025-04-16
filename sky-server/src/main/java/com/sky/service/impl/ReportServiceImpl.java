package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 营业额统计
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {

        //用于存放从begin到end每一天的日期
        List<LocalDate> dateList=new ArrayList<>();
        List<Double> turnoverList=new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)) {
            begin=begin.plusDays(1);
            dateList.add(begin);
        }

        String date = StringUtils.join(dateList, ",");

        for (LocalDate dt : dateList) {
            //查询date日期对应的营业额数据，状态为已完成的订单金额合计
            LocalDateTime beginTime = LocalDateTime.of(dt, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(dt, LocalTime.MAX);

            Map mp=new HashMap();
            mp.put("begin",beginTime);
            mp.put("end",endTime);
            mp.put("status", Orders.COMPLETED);

            Double turnover= orderMapper.sumByMap(mp);
            turnover=turnover==null?0:turnover;
            turnoverList.add(turnover);
        }
        String turnOverStr = StringUtils.join(turnoverList, ",");

        return TurnoverReportVO.
                builder()
                .dateList(date)
                .turnoverList(turnOverStr)
                .build();
    }
}
