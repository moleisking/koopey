package com.koopey.model;

import com.koopey.model.base.Base;

import java.util.Comparator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
//@ToString
public class Tag extends Base  {

    String de;
    String en;
    String es;
    String fr;
    String it;
    String pt;
    String zh;

  /*  @Override
    public boolean equals(Tag o1, Tag o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getId(), o2.getId());
    }*/

    public String getText(String language) {
        if (language.equals("de")) {
            return this.de;
        } else if (language.equals("en")) {
            return this.en;
        } else if (language.equals("es")) {
            return this.es;
        } else if (language.equals("fr")) {
            return this.fr;
        } else if (language.equals("it")) {
            return this.it;
        } else if (language.equals("pt")) {
            return this.pt;
        } else if (language.equals("zh")) {
            return this.zh;
        } else {
            return this.en;
        }
    }

}
