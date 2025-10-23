package it.schwarz.jobs.review.coupon.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.schwarz.jobs.review.coupon.api.dto.applycoupon.ApplyCouponRequestDto;
import it.schwarz.jobs.review.coupon.api.dto.createcoupon.CreateCouponRequestDto;
import it.schwarz.jobs.review.coupon.domain.*;
import it.schwarz.jobs.review.coupon.service.CouponService;
import it.schwarz.jobs.review.coupon.testobjects.TestCoupons;
import it.schwarz.jobs.review.coupon.testobjects.TestObjects;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CouponRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CouponService couponService;


    @Test
    void testGetCoupons() throws Exception {
        when(couponService.findAllCoupons()).thenReturn(new ArrayList<>());

        this.mockMvc
                .perform(get("/api/coupons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("coupons")));

        verify(couponService).findAllCoupons();
    }

    @Test
    void testCreateValidCoupon() throws Exception {

        CreateCouponRequestDto request = TestObjects.requests().validCoupon();
        ArgumentCaptor<Coupon> captor = ArgumentCaptor.forClass(Coupon.class);
        when(couponService.createCoupon(any())).thenReturn(TestObjects.coupons().COUPON_12_20());

        this.mockMvc
                .perform(post("/api/coupons")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        verify(couponService).createCoupon(captor.capture());
        assertEquals(captor.getValue().getCode(), request.code());
    }

    @Test
    void testCreateInvalidCoupon() throws Exception {

        CreateCouponRequestDto request = TestObjects.requests().invalidCouponOfNegativeDiscount();

        this.mockMvc
                .perform(post("/api/coupons")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testGetValidCouponApplications() throws Exception {
        String coupon = TestObjects.coupons().COUPON_12_20().getCode();

        when(couponService.getApplications(coupon)).thenReturn(new CouponApplications(coupon, List.of(Instant.now())));

        this.mockMvc
                .perform(get("/api/coupons/{couponCode}/applications", TestObjects.coupons().COUPON_12_20().getCode()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        verify(couponService).getApplications(coupon);
    }

    @Test
    void testApplyValidCoupon() throws Exception {

        ArgumentCaptor<Basket> captor = ArgumentCaptor.forClass(Basket.class);
        ApplyCouponRequestDto request = TestObjects.requests().validApplication();
        when(couponService.applyCoupon(any(), any())).thenReturn(new ApplicationResult(request.basket().toBasket(), new Coupon("test", AmountOfMoney.of("12.00"), AmountOfMoney.of("20.00"), "12 for 20")));

        this.mockMvc
                .perform(post("/api/coupons/applications")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        verify(couponService).applyCoupon(captor.capture(), eq(request.couponCode()));
        assertEquals(captor.getValue().getValue().toBigDecimal(), request.basket().value());
    }

    @Test
    void testApplyInvalidCoupon() throws Exception {

        ApplyCouponRequestDto request = TestObjects.requests().invalidApplicationOfEmptyCode();

        this.mockMvc
                .perform(post("/api/coupons/applications")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

    }


}