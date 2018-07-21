package com.interview.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.ex.BadRequestException;
import com.interview.ex.NotFoundException;
import com.interview.ex.WorkflowExistedException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by Zephyr Lin
 * User: zephyr
 * Date: 7/20/18
 * Time: 3:50 PM
 */
public abstract class BaseRestController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseRestController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(BadRequestException.class)
    public void handleBadRequest(HttpServletResponse response, BadRequestException ex) {
        LOG.error("Handling bad request...", ex);
        sendErrorResponse(response, HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(HttpServletResponse response, NotFoundException ex) {
        LOG.error("Handling not found exception...", ex);
        sendErrorResponse(response, HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(WorkflowExistedException.class)
    public void handleWorkflowExistedException(HttpServletResponse response, WorkflowExistedException ex) {
        LOG.error("Handling workflow existed exception...", ex);
        sendErrorResponse(response, HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(Exception.class)
    public void handleGeneralException(HttpServletResponse response, Exception ex) {
        LOG.error("Handling general exception...", ex);
        sendErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus httpStatus, Exception ex) {
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(httpStatus.value());

        try {
            IOUtils.copy(new ByteArrayInputStream(objectMapper.writeValueAsString(
                new WorkflowResponse<>(ex.getMessage())).getBytes()),
                response.getOutputStream());
        } catch (IOException ioe) {
            LOG.error("Failed to modify response stream", ioe);
        }
    }
}
