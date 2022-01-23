package com.lxf.data;

import com.lxf.Annotation.RouterBean;
import com.lxf.Annotation.RouterMethod;
import com.lxf.Router.CallBackHandler;
import com.lxf.protocol.RouteBean;
import com.lxf.Router.RareCore;
import com.lxf.protocol.CheckResult;
import com.lxf.protocol.Checker;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
//        if (result.parameterArray[index] == null) {
//            return true;
//        }
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
        集合数据,:
        1、list;
        2、map;
        3、set;
         */
//        if (askCls == replyCls && askStr.startsWith("java.util.") && replyStr.startsWith("java.util.")) {
//            /* 如果两个类型相同，编译时产生的askStr、replyStr却不一样，说明一定有泛型。*/
//            return containerConvert(askStr, askCls, replyStr, replyCls, index);
//        }

        /* 查看是否为：RareParcelable类型. */
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


        return false;
    }


    private boolean containerConvert(String askStr, Class<?> askCls, String replyStr, Class<?> replyCls, int index) {
        /*
        取出泛型里面的 字符串：askStrNew、replyStrNew。
        replyStrNew、replyStrNew 肯定不相等，否则走不到这一步。
        所以，replyStrNew、replyStrNew 只能是 泛型 或者 RouterParcelable类型。
        换句话：如果不包含泛型，又不是RouterParcelable类型，无法进行转换。
         */
        int askGrcIndex = askStr.indexOf("<");
        int replyGrcIndex = askStr.indexOf("<");
        if (askGrcIndex < 0 || replyGrcIndex < 0) {
            return false;
        }
        String askStrNew = askStr.substring(askGrcIndex + 1, askStr.length() - 1);
        String replyStrNew = replyStr.substring(replyGrcIndex + 1, replyStr.length() - 1);

        boolean isAskGrc = askStrNew.contains("<");
        boolean isReplyGrc = replyStrNew.contains("<");

        if (isAskGrc != isReplyGrc) {
            return false;
        }

        /* 如果都是 List、ArrayList、LinkedList 类型 */
        if (askCls == List.class && replyCls == List.class) {

        } else if (askCls == ArrayList.class && replyCls == ArrayList.class) {

        } else if (askCls == LinkedList.class && replyCls == LinkedList.class) {

        } else if (askCls == ArrayList.class && replyCls == ArrayList.class) {

        } else if (askCls == Set.class && replyCls == Set.class) {

        } else if (askCls == HashSet.class && replyCls == HashSet.class) {

        } else if (askCls == Map.class && replyCls == Map.class) {

        } else if (askCls == HashMap.class && replyCls == HashMap.class) {

        }
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
            obj = (RouterParcelable) RareCore.getRareCore().beanCreator(bean.path()).createInstance();
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
