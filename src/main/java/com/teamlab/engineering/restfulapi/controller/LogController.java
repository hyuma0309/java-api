package com.teamlab.engineering.restfulapi.controller;

import com.teamlab.engineering.restfulapi.entitiy.Log;
import com.teamlab.engineering.restfulapi.form.LogForm;
import com.teamlab.engineering.restfulapi.service.LogService;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Logコントローラー
 *
 * @author asada
 */
@Controller
@Slf4j
public class LogController {
  private final LogService logService;

  public LogController(LogService logService) {
    this.logService = logService;
  }

  @GetMapping(value = "/api-log")
  public String logGet(@ModelAttribute LogForm logForm) {
    return "api-log";
  }

  @PostMapping(value = "/api-log/search")
  public String serchLog(
      Model model, @Validated @ModelAttribute LogForm logForm, BindingResult bindingResult) {
    if (logForm.getStartDate() != null && logForm.getEndDate() != null) {
      if (logForm.getStartDate().isAfter(logForm.getEndDate())) {
        bindingResult.rejectValue("endDate", null, "日付が超えています");
      }
    }

    if (bindingResult.hasErrors()) {
      return "api-log";
    }
    List<Log> aggregateList =
        logService.searchAggregate(logForm.getStartDate(), logForm.getEndDate());
    model.addAttribute("aggregateList", aggregateList);
    return "api-log";
  }
}
