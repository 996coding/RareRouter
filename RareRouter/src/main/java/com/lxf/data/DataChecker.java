package com.lxf.data;

import com.lxf.Annotation.RouterBean;
import com.lxf.Annotation.RouterMethod;
import com.lxf.Router.CallBackHandler;
import com.lxf.protocol.RouteBean;
import com.lxf.Router.RareAppImpl;
import com.lxf.protocol.CheckResult;
import com.lxf.protocol.Checker;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class DataChecker implements Checker {

    private RouteBean askBean, replyBean;
    private Method askMethod, replyMethod;
    private String annotation;
    private CheckResult result;

    public DataChecker(RouteBean askBean, Method askMethod) {
        this.askBean = askBean;
        this.askMethod = askMethod;
        this.annotation = askMethod.getAnnotation(RouterMethod.class).path();
        this.result = new CheckResult();
    }

    @Override
    public CheckResult methodCheck(RouteBean replyBean, Class<?> replyClazz, Object... parameterArray) {
        result.isOk = false;

        if (annotation == null || annotation.length() == 0) {
            return result;
        }

        this.replyBean = replyBean;
        for (Method m : replyClazz.getMethods()) {
            RouterMethod replyAnnotate = m.getAnnotation(RouterMethod.class);
            if (replyAnnotate != null && annotation.equals(replyAnnotate.path())) {
                replyMethod = m;
                break;
            }
        }

        if (replyMethod == null) {
            return result;
        }

        if (!returnCheck()) {
            return result;
        }
        Class<?>[] parameters = askMethod.getParameterTypes();
        if (parameters.length != parameterArray.length) {
            return result;
        }
        result.parameterArray = parameterArray;
        for (int i = 0; i < parameters.length; i++) {
            if (!parameterCheck(askBean.paramsList.get(i), askMethod.getParameterTypes()[i], replyBean.paramsList.get(i), replyMethod.getParameterTypes()[i], i)) {
                return result;
            }
        }
        result.isOk = true;
        return result;
    }

    @Override
    public boolean instanceCheck(Object instance, Class<?> clazz) {
        if (instance != null && clazz.isInstance(instance)) {
            return true;
        }
        return false;
    }


    private boolean returnCheck() {
        if (askBean.returnType.equals(replyBean.returnType)) {
            return true;
        }

        return false;
    }

    private boolean parameterCheck(String askStr, Class<?> askCls, String replyStr, Class<?> replyCls, int index) {
        /*
        只能解析：
        1、基本数据类型；
        2、String；
        3、自定义数据类型，Parcelable；
        4、接口；
        以及集合类：
        1、List；
        2、Map；
        3、Set；
         */
        if (result.parameterArray[index] == null) {
            return true;
        }
        if (askStr.equals(replyStr)) {
            return true;
        }
        /*
        如果不相等，可能由于：
        1、自定义数据类型，Parcelable；
        2、接口；
        3、泛型。
         */
//        int askBasicTypeID = DataType.getBasicTypeID(askStr);
//        int replyBasicTypeID = DataType.getBasicTypeID(replyStr);
//
//        if (askBasicTypeID > 0 && askBasicTypeID == replyBasicTypeID) {
//            return true;
//        }
//
        /*
        查看是否为：RareParcelable类型.
         */
        if (isRareParcelable(askCls) && isRareParcelable(replyCls)) {
            //转换
            RouterParcelable replyParcelable = createRareParcelable(replyCls);
            convert((RouterParcelable) result.parameterArray[index], replyParcelable);
            result.parameterArray[index] = replyParcelable;
            return true;
        }

        if (askCls.isInterface() && replyCls.isInterface()) {
            result.parameterArray[index] = Proxy.newProxyInstance(replyCls.getClassLoader(), new Class<?>[]{replyCls},
                    new CallBackHandler(replyCls, result.parameterArray[index], askCls));
            return true;
        }

        /*
        集合数据,:
        1、list;
        2、map;
        3、set;
         */
//        if (askCls == java.util.List.class && replyCls == java.util.List.class) {
//            List aimList = new ArrayList();
//            List sourceList = (List) result.parameterArray[index];
//            if (listConvert(sourceList, aimList, askStr, replyStr)) {
//                result.parameterArray[index] = aimList;
//                return true;
//            } else {
//                return false;
//            }
//        }

        return false;
    }

    private boolean listConvert(List sourceList, List aimList, String askStr, String replyStr) {

        /* askStrNew 和 replyStrNew 肯定不会相等，否则走不到这一步 */
        String askStrNew = askStr.substring("java.util.List<".length(), askStr.length() - 1);
        String replyStrNew = replyStr.substring("java.util.List<".length(), replyStr.length() - 1);

        /* askStrNew 和 replyStrNew 肯定不是基本数据类型，否则不可能不相等 */
        /* askStrNew 和 replyStrNew 看下收否还存在泛型 */
        boolean askGeneric = askStrNew.contains("<");
        boolean replyGeneric = replyStr.contains("<");
        if (askGeneric && replyGeneric) {
            /* askStrNew 和 replyStrNew 都是泛型 */


        } else if (!askGeneric && !replyGeneric) {
            /* askStrNew 和 replyStrNew 都不是泛型，只能是 RareParcelable，否则类型检测失败 */

        } else {
            return false;
        }
        return false;

    }

    private boolean isRareParcelable(Class<?> clazz) {
        if (clazz == RouterParcelable.class) {
            return true;
        }
        Class<?>[] interfaceClazzArr = clazz.getInterfaces();
        if (interfaceClazzArr != null && interfaceClazzArr.length > 0) {
            for (Class<?> item : interfaceClazzArr) {
                if (isRareParcelable(item)) {
                    return true;
                }
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            if (isRareParcelable(superClazz)) {
                return true;
            }
        }
        return false;
    }

    private RouterParcelable createRareParcelable(Class<?> replyCls) {
        RouterBean bean = replyCls.getAnnotation(RouterBean.class);
        RouterParcelable obj = null;
        if (bean == null || bean.path().length() == 0) {
            try {
                obj = (RouterParcelable) replyCls.newInstance();
            } catch (Exception e) {
            }
        } else {
            obj = (RouterParcelable) RareAppImpl.getRareAppImpl().beanCreator(bean.path()).createInstance();
        }
        return obj;
    }

    private boolean convert(RouterParcelable source, RouterParcelable des) {
        if (source == null || des == null) {
            return false;
        }
        DataConvert convert = new DataConvert();
        source.routerParcelableRead(convert);
        des.routerParcelableWrite(convert);
        return true;
    }
}
