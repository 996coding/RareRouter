package com.lxf.Process.configure;

import com.lxf.Process.genTxt.TxtReader;
import com.lxf.Process.genTxt.TxtWriter;

public class ScanIndex {
    private static int SCAN_INDEX = -1, SCAN_NUM = -1;
    private static final int NUM_SIGN = 100;

    public static String genScanIndexInfo(int total, int index) {
        int scan_info = total * NUM_SIGN + index;
        return scan_info + "";
    }

    public static boolean isLastScan() {
        return getScanIndex() == getScanTotal();
    }

    public static boolean isFirstScan() {
        return getScanIndex() == 1;
    }

    public static int getScanIndex() {
        return SCAN_INDEX;
    }

    public static int getScanTotal() {
        return SCAN_NUM;
    }

    public static void initScanInfo() {
        int moduleInfo = readScanIndex();
        SCAN_NUM = moduleInfo / NUM_SIGN;
        SCAN_INDEX = moduleInfo % NUM_SIGN % SCAN_NUM + 1;
        String scanInfo = ScanIndex.genScanIndexInfo(SCAN_NUM, SCAN_INDEX);
        TxtWriter.writeTxt(TxtPath.PATH_SCAN_INDEX, scanInfo);
    }

    private static int readScanIndex() {
        String content = TxtReader.readTxt(TxtPath.PATH_SCAN_INDEX).trim();
        if (content == null || content.length() == 0) {
            return 0;
        }
        int res = Integer.parseInt(content);
        return res;
    }
}
