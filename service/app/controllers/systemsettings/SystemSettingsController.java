package controllers.systemsettings;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.BaseController;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;
import org.sunbird.common.models.util.ActorOperations;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.LoggerEnum;
import org.sunbird.common.models.util.ProjectLogger;
import org.sunbird.common.request.Request;
import org.sunbird.validator.systemsettings.SystemSettingsRequestValidator;
import play.libs.F.Promise;
import play.mvc.Result;

public class SystemSettingsController extends BaseController {

  private static final SystemSettingsRequestValidator systemSettingsRequestValidator =
      new SystemSettingsRequestValidator();

  @SuppressWarnings("unchecked")
  public Promise<Result> getSystemSetting(String settingId) {
    try {
      ProjectLogger.log(
          "SystemSettingsController: getSystemSetting called", LoggerEnum.INFO.name());
      Request reqObj = createAndInitRequest(ActorOperations.GET_SYSTEM_SETTING.getValue(), null);
      Map<String, Object> map = new HashedMap();
      map.put(JsonKey.ID, settingId);
      reqObj.setRequest(map);
      return actorResponseHandler(getActorRef(), reqObj, timeout, null, request());
    } catch (Exception e) {
      return Promise.<Result>pure(createCommonExceptionResponse(e, request()));
    }
  }

  @SuppressWarnings("unchecked")
  public Promise<Result> setSystemSetting() {
    try {
      ProjectLogger.log(
          "SystemSettingsController: setSystemSetting called", LoggerEnum.INFO.name());
      JsonNode requestJson = request().body().asJson();
      Request reqObj =
          createAndInitRequest(ActorOperations.SET_SYSTEM_SETTING.getValue(), requestJson);
      systemSettingsRequestValidator.validateSetSystemSetting(reqObj);
      return actorResponseHandler(getActorRef(), reqObj, timeout, null, request());
    } catch (Exception e) {
      return Promise.<Result>pure(createCommonExceptionResponse(e, request()));
    }
  }

  @SuppressWarnings("unchecked")
  public Promise<Result> getAllSystemSettings() {
    try {
      ProjectLogger.log(
          "SystemSettingsController: getAllSystemSettings called", LoggerEnum.INFO.name());
      Request reqObj =
          createAndInitRequest(ActorOperations.GET_ALL_SYSTEM_SETTINGS.getValue(), null);
      return actorResponseHandler(getActorRef(), reqObj, timeout, null, request());
    } catch (Exception e) {
      return Promise.<Result>pure(createCommonExceptionResponse(e, request()));
    }
  }
}
