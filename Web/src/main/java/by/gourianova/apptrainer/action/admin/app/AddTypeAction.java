package by.gourianova.apptrainer.action.admin.app;

import by.gourianova.apptrainer.action.Action;
import by.gourianova.apptrainer.controller.Router;
import by.gourianova.apptrainer.entity.AppType;
import by.gourianova.apptrainer.exception.ServiceException;
import by.gourianova.apptrainer.service.AppTypeService;
import by.gourianova.apptrainer.util.PageConstant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;


public class AddTypeAction implements Action {
    private final static String TYPE_TYPE = "type";
    private final static String TYPE_PRICE = "price";
    private final static String TYPE_PHOTO = "photo";
    private final static String MESSAGE = "message";
    private final static String ADMIN_PAGE = "/controller?action=show_admin_page";
    private AppTypeService appTypeService = new AppTypeService();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router = new Router();
        AppType appType = new AppType();
        try {
            appType.setType(request.getParameter(TYPE_TYPE));
            appType.setPrice(new BigDecimal(request.getParameter(TYPE_PRICE)));
            Part filePart = request.getPart(TYPE_PHOTO);
            InputStream inputStream = filePart.getInputStream();
            appTypeService.create(appType, inputStream);
            router.setPagePath(ADMIN_PAGE);
            router.setRoute(Router.RouteType.REDIRECT);
        } catch (ServiceException | ServletException | IOException e) {
            request.getSession().setAttribute(MESSAGE, e.getMessage());
            router.setPagePath(PageConstant.ERROR_PAGE);
            router.setRoute(Router.RouteType.REDIRECT);
        }
        return router;
    }
}
