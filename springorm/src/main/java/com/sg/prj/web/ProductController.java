package com.sg.prj.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sg.prj.entity.Product;
import com.sg.prj.service.OrderService;

@Controller
public class ProductController {
	@Autowired
	private OrderService service;

	@RequestMapping("getProducts.do")
	public ModelAndView getProducts() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("products", service.getProducts());
		mav.setViewName("print.jsp");
		return mav;
	}

	@RequestMapping("getProductForm.do")
	public ModelAndView productForm() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("product", new Product());
		mav.setViewName("form.jsp");
		return mav;
	}
	
	@RequestMapping("addProduct.do")
	public ModelAndView addProduct(@ModelAttribute("product") Product product) {
		ModelAndView mav = new ModelAndView();
		service.addProduct(product);
		mav.addObject("msg", "Product added!!!");
		mav.setViewName("redirect:index.jsp");
		return mav;
	}
	
//	@RequestMapping("addProduct.do")
//	public ModelAndView addProductDirect(HttpServletRequest req){
//		Product product = new Product();
//		product.setName(req.getParameter("name"));
//		ModelAndView mav = new ModelAndView();
//		service.addProduct(product);
//		mav.addObject("msg", "Product added!!!");
//		mav.setViewName("index.jsp");
//		return mav;
//	}
}
