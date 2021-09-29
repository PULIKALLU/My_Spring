package com.sg.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sg.prj.dto.ReportDTO;
import com.sg.prj.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer>{
	@Query("select new com.sg.prj.dto.ReportDTO(o.orderDate, o.total, c.email, c.firstName, c.lastName) "
			+ " from Order o inner join o.customer c")
	List<ReportDTO> getReport();
}
