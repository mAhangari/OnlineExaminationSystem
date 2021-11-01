package ir.maktab56.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExamController {

    public ExamController() {

    }

    @GetMapping(value = "/home")
    public String homePage() {
        return "redirect:/";
    }
//
//
    @RequestMapping(value = "/login")
    public String loginForm() {
        return "views/sign-in";
    }
//
//    @PostMapping("/user-page")
//    public String userPage() {
//        return "views/customer-profile";
//    }
//


    @GetMapping(value = "/sign-up")
    public String signUp() {
        return "views/sign-up";
    }

//    @GetMapping(value = "/search-ticket")
//    public String searchTicket(HttpServletRequest request, Model model) {
//        String source = request.getParameter("source");
//        String destination = request.getParameter("destination");
//        LocalDate date = LocalDate.parse(request.getParameter("date"));
//
//        List<TravelSchedule> travelSchedules = travelScheduleService.findBySourceAndDestinationAndTravelDateOrderByTravelTimeAsc(source, destination, date);
//
//        model.addAttribute("travelSchedules", travelSchedules);
//
//        return "views/ticket-list";
//    }
//
//    @GetMapping(value = "/buy-ticket")
//    public String buyTicket(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        session.setAttribute("ticketId", request.getParameter("ticketId"));
//        return "views/ticket-specification";
//    }
//
//    @GetMapping(value = "/submit-ticket")
//    public String submitTicket(@RequestParam String gender, @RequestParam String fullName, HttpSession session, Model model) {
//
//        Ticket ticket = new Ticket();
//        ticket.setTicketId(session.getAttribute("ticketId") + "1");
//        ticket.setTravelId((String) session.getAttribute("ticketId"));
//
//        TravelSchedule travelSchedule = travelScheduleService.findByTravelId((String) session.getAttribute("ticketId"));
//
//        ticket.setSource(travelSchedule.getSource());
//        ticket.setDestination(travelSchedule.getDestination());
//        ticket.setTravelTime(travelSchedule.getTravelTime());
//        ticket.setTravelDate(travelSchedule.getTravelDate());
//        ticket.setFullName(fullName);
//        ticket.setGender(gender);
//        ticket.setCustomer((Customer) session.getAttribute("account"));
//        ticketService.save(ticket);
//
//        model.addAttribute("fullName", fullName);
//        if (gender.equals("male"))
//            model.addAttribute("gender", "آقای");
//        else model.addAttribute("gender", "خانم");
//        model.addAttribute("ticketId", ticket.getTicketId());
//
//        return "views/success-buy-page";
//    }
//
//    @GetMapping(value = "/ticket-inquiry")
//    public String ticketInquiry(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession(false);
//        List<Ticket> tickets = ticketService.findByCustomer((Customer) session.getAttribute("account"));
//        model.addAttribute("tickets", tickets);
//        return "views/purchased-tickets";
//    }
//
//    @GetMapping(value = "/ticket-information")
//    public String ticketInformation(@RequestParam String ticketId, Model model) {
//        Ticket ticket = ticketService.findById(Long.parseLong(ticketId));
//        model.addAttribute("ticket", ticket);
//        return "views/ticket-info";
//    }
//
//    @GetMapping(value = "/refund-ticket")
//    public String refundTicket(@RequestParam String ticketId) {
//        ticketService.deleteById(Long.parseLong(ticketId));
//        return "views/refund-ticket-message";
//    }
}
