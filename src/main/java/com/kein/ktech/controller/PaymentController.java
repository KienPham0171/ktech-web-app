package com.kein.ktech.controller;

import com.kein.ktech.constant.InvoiceStatus;
import com.kein.ktech.domain.*;
import com.kein.ktech.security.CustomOauth2User;
import com.kein.ktech.security.CustomUserDetails;
import com.kein.ktech.service.CartService;
import com.kein.ktech.service.InvoiceInfoService;
import com.kein.ktech.service.InvoiceService;
import com.kein.ktech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PaymentController {
    InvoiceService invoiceService;
    InvoiceInfoService invoiceInfoService;
    UserService userService;
    CartService cartService;
    @Autowired
    public PaymentController(InvoiceService invoiceService, InvoiceInfoService invoiceInfoService
                            ,UserService userService,CartService cartService) {
        this.invoiceService = invoiceService;
        this.invoiceInfoService = invoiceInfoService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping("/create-payment")
    public Object createPayment(@Valid Payment paymentInfo,BindingResult result,Model model,
                                      HttpServletRequest req) throws ServletException, IOException, NoSuchAlgorithmException, InvalidKeyException {
            if(result.hasErrors()){
            User user = getUser(SecurityContextHolder.getContext().getAuthentication());
            Cart cart = null;
            Optional<Cart> opt =cartService.getCartIsNotCheckOutById(user.getId());
            if(opt.isPresent()) {
                cart = opt.get();
            } else {
                cart = new Cart();
                cart.setUserId(user);
                cartService.createNewCart(cart);
            };
            double invoiceTotal= cart.getCartLines().stream().mapToDouble(line -> line.getPrice() * line.getQuantity()).sum();
            List<Address> addresses = user.getAddresses();
            model.addAttribute("invoiceTotal",invoiceTotal);
            model.addAttribute("user",user);
            if(addresses.size()>0){
                    model.addAttribute("addresses",addresses);}
            if(result.hasFieldErrors("address")) model.addAttribute("addressError","Address not valid!");
            return "checkout";
        }
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_OrderInfo = "test";
            String orderType = "150000";
            Random rand = new Random();
            BigInteger b = new BigInteger(64, new Random());
            String vnp_TxnRef = String.valueOf(b);
            String vnp_IpAddr = req.getRemoteAddr();
            String vnp_TmnCode = "HPNY0DMH";
            String address = paymentInfo.getAddress();
            String returnUrl = "http://localhost:8080/payment-result"+ "?"+ "address="+address;

            //int amount = Integer.parseInt(paymentInfo.getInvoiceTotal()) * 100;
            int amount =(int) paymentInfo.getInvoiceTotal()*100*23000;
            Map<String,String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            String bank_code = "NCB";
            vnp_Params.put("vnp_BankCode", bank_code);
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
            vnp_Params.put("vnp_OrderType", orderType);
            vnp_Params.put("vnp_Locale", "vn");

           // vnp_Params.put("vnp_ReturnUrl", "http://localhost:8080/payment-result");
            vnp_Params.put("vnp_ReturnUrl", returnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());

            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            vnp_Params.put("vnp_Inv_Address",paymentInfo.getAddress());


            //Build data to hash and querystring
            List<String> fieldNames = new ArrayList<String>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName =  itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String secret ="SJPLTPVQNVVPXEUNYUJERJUTUPKLATFP";
            String vnp_SecureHash = hmacSHA512(secret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html" + "?" + queryUrl;


        return new RedirectView(paymentUrl);
    }
    @GetMapping("/payment-result")
    public String result(InvoiceInfo invoiceInfo,
                         @RequestParam Map<String,String> allParams, Model model){
        String result = allParams.get("vnp_ResponseCode");
        String address = allParams.get("address");
        Invoice invoice = null;
        if(result.compareTo("00")==0){
            model.addAttribute("result","Successfully");
            invoice = new Invoice();
            Calendar cld = Calendar.getInstance();
            Date date =  cld.getTime();
            invoice.setInvoiceRef("VNP"+invoiceInfo.getVnp_TxnRef());
            invoice.setType("VNP");
            invoice.setCreatedDate(date);
            invoice.setInvoiceTotal(Double.parseDouble(invoiceInfo.getVnp_Amount())*0.01);
            invoice.setStatus(InvoiceStatus.COMPLETED);
            invoice.setInvoiceInfo(invoiceInfo);
            invoice.setAddress(address);
            User user = getUser(SecurityContextHolder.getContext().getAuthentication());
            invoice.setUser(user);
            Optional<Cart> c = cartService.getCartIsNotCheckOutById(user.getId());
            invoiceInfo.setInvoice_id(invoice);
            if(c.isPresent()){
                Cart cart = c.get();
                invoice.setCart(cart);
                cart.setCheckOut(true);
                cartService.updateCart(cart);
            }
            else{
                Cart cart = new Cart();
                invoice.setCart(cart);
            }

            //save invoice
            invoiceService.saveInvoice(invoice);

        }else{
            model.addAttribute("result","Failure");
        }
        model.addAttribute("invoice",invoice);
        return "paymentResult";
    }
    @PostMapping("/create-order")
    public String createOrder(@Valid Payment paymentInfo, BindingResult result, Model model){
        if(result.hasErrors()){
            User user = getUser(SecurityContextHolder.getContext().getAuthentication());
            Cart cart = null;
            Optional<Cart> opt =cartService.getCartIsNotCheckOutById(user.getId());
            if(opt.isPresent()) {
                cart = opt.get();
            } else {
                cart = new Cart();
                cart.setUserId(user);
                cartService.createNewCart(cart);
            };
            double invoiceTotal= cart.getCartLines().stream().mapToDouble(line -> line.getPrice() * line.getQuantity()).sum();
            List<Address> addresses = user.getAddresses();
            model.addAttribute("invoiceTotal",invoiceTotal);
            model.addAttribute("user",user);
            if(addresses.size()>0){
                model.addAttribute("addresses",addresses);}
            if(result.hasFieldErrors("address")) model.addAttribute("addressError","Address not valid!");
            return "checkout";
        }else{
            Invoice invoice = new Invoice();
            Calendar cld = Calendar.getInstance();
            Date date =  cld.getTime();
            BigInteger b = new BigInteger(64, new Random());

            System.out.println(b);
            invoice.setInvoiceRef("ORDER"+b);
            invoice.setType("ORDER");
            invoice.setCreatedDate(date);
            invoice.setInvoiceTotal(paymentInfo.getInvoiceTotal()*23000);
            invoice.setStatus(InvoiceStatus.NOT_CONFIRMED);

            invoice.setAddress(paymentInfo.getAddress());
            User user = getUser(SecurityContextHolder.getContext().getAuthentication());
            invoice.setUser(user);
            Optional<Cart> c = cartService.getCartIsNotCheckOutById(user.getId());
            if(c.isPresent()){
                Cart cart = c.get();
                invoice.setCart(cart);
                cart.setCheckOut(true);
                cartService.updateCart(cart);
            }
            else{
                Cart cart = new Cart();
                invoice.setCart(cart);
            }
            invoiceService.saveInvoice(invoice);


            model.addAttribute("result","Successfully!");
            model.addAttribute("invoice",invoice);
            return "paymentResult";
        }


        //save invoice

    }

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
    public User getUser(Authentication authentication)
    {

        var principal = authentication.getPrincipal();
        if(principal instanceof OAuth2User){
            CustomOauth2User user = new CustomOauth2User((OAuth2User)authentication.getPrincipal());
            return  userService.getUserByEmail(user.getEmail());

        }else{
            if(principal instanceof CustomUserDetails)
            {
                CustomUserDetails user = (CustomUserDetails) principal;
                return userService.getUserByEmail(user.getEmail());
            }

//            CustomUserDetails user = (CustomUserDetails) principal;
//            System.out.println("log by regular user: "+ user.getUsername());
//            userName = user.getUsername();
        }
        return null;
    }
}
