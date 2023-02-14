package bj.tresorbenin.suicom.session;

import bj.tresorbenin.suicom.utils.JavaUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class MapFlash implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Object> map;

    public MapFlash() {}

    public <E> MapFlash(String key, E value) {
        setMap(JavaUtils.doPut(null, key, value));
    }

    @SuppressWarnings("unchecked")
    public <E> Map<String, E> getMap() {
        return (Map<String, E>) map;
    }

    public void setMap(Map<String, Object> mapInfo) {
        this.map = mapInfo;
    }
}
