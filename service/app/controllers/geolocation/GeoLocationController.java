package controllers.geolocation;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.BaseController;
import java.util.Map;
import org.sunbird.common.models.util.ActorOperations;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.LoggerEnum;
import org.sunbird.common.models.util.ProjectLogger;
import org.sunbird.common.request.ExecutionContext;
import org.sunbird.common.request.Request;
import play.libs.F.Promise;
import play.mvc.Result;

/**
 * Created by arvind on 31/10/17.
 */
public class GeoLocationController extends BaseController {


  public Promise<Result> createGeoLocation() {
    try {
      JsonNode requestData = request().body().asJson();
      ProjectLogger.log("create geo location" + requestData, LoggerEnum.INFO.name());
      Request reqObj = (Request) mapper.RequestMapper.mapRequest(requestData, Request.class);
      reqObj.setOperation(ActorOperations.CREATE_GEO_LOCATION.getValue());
      reqObj.setRequestId(ExecutionContext.getRequestId());
      reqObj.setEnv(getEnvironment());
      Map<String, Object> innerMap = reqObj.getRequest();
      innerMap.put(JsonKey.REQUESTED_BY, ctx().flash().get(JsonKey.USER_ID));
      reqObj.setRequest(innerMap);
      return actorResponseHandler(getActorRef(), reqObj, timeout, null, request());
    } catch (Exception e) {
      return Promise.<Result>pure(createCommonExceptionResponse(e, request()));
    }
  }

  public Promise<Result> getGeoLocation(String id) {
    try {
      ProjectLogger.log("get geo location by id " );
      String type = request().getQueryString(JsonKey.TYPE);
      Request reqObj = new Request();
      reqObj.setOperation(ActorOperations.GET_GEO_LOCATION.getValue());
      reqObj.setRequestId(ExecutionContext.getRequestId());
      reqObj.setEnv(getEnvironment());
      Map<String, Object> innerMap = reqObj.getRequest();
      innerMap.put(JsonKey.REQUESTED_BY, ctx().flash().get(JsonKey.USER_ID));
      innerMap.put(JsonKey.TYPE , type);
      innerMap.put(JsonKey.ID , id);
      reqObj.setRequest(innerMap);
      return actorResponseHandler(getActorRef(), reqObj, timeout, null, request());
    } catch (Exception e) {
      return Promise.<Result>pure(createCommonExceptionResponse(e, request()));
    }
  }

}
