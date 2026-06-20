/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.guide;

public final class SkillGuideEntry {
    String label;
    int itemId;
    private int requiredLevel;
    private boolean membersOnly;
    boolean suppressCategoryPrefix;

    public SkillGuideEntry(String string) {
        this.label = string;
        this.itemId = -1;
        this.requiredLevel = -1;
        this.membersOnly = false;
        this.suppressCategoryPrefix = false;
    }

    public SkillGuideEntry(String string, int n) {
        this.label = string;
        this.itemId = n;
        this.requiredLevel = -1;
        this.membersOnly = false;
        this.suppressCategoryPrefix = false;
    }

    public SkillGuideEntry(int n, String string, int n2) {
        this.label = string;
        this.itemId = n2;
        this.requiredLevel = n;
        this.membersOnly = false;
        this.suppressCategoryPrefix = false;
    }

    public SkillGuideEntry(int n, boolean bl, String string, int n2) {
        this.label = string;
        this.itemId = n2;
        this.requiredLevel = n;
        this.membersOnly = false;
        this.suppressCategoryPrefix = true;
    }

    public SkillGuideEntry(int n, String string, int n2, boolean bl) {
        this.label = string;
        this.itemId = n2;
        this.requiredLevel = n;
        this.membersOnly = true;
        this.suppressCategoryPrefix = false;
    }

    public final String getDisplayLabel() {
        if (this.membersOnly) {
            return "Members: " + this.label;
        }
        return this.label;
    }

    public final String getLevelText() {
        return "" + this.requiredLevel;
    }
}

