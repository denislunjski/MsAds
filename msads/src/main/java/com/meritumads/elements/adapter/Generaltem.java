package com.meritumads.elements.adapter;

import com.meritumads.pojo.Banner;
import com.meritumads.pojo.Position;

public class Generaltem extends Item {

    Banner banner;
    Position position;

    int type = 0;

    public Generaltem(Banner banner, int type) {
        this.banner = banner;
        this.type = type;
    }

    public Generaltem(Position position, int type) {
        this.position = position;
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public Banner getBanner() {
        return banner;
    }

    public int getType() {
        return type;
    }

    @Override
    public int getTypeItem() {
        return type;
    }
}
