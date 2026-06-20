/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net;

public final class IsaacCipher {
    private int a = 0;
    private int[] b = new int[256];
    private int[] c = new int[256];
    private int d;
    private int e;
    private int f;

    public IsaacCipher(int[] nArray) {
        int n = 0;
        while (n < 4) {
            this.b[n] = nArray[n];
            ++n;
        }
        this.a(true);
    }

    public final int a() {
        if (this.a-- == 0) {
            this.b();
            this.a = 255;
        }
        return this.b[this.a];
    }

    private void b() {
        int n;
        int n2;
        this.e += ++this.f;
        int n3 = 0;
        int n4 = 128;
        while (n3 < 128) {
            n2 = this.c[n3];
            this.d ^= this.d << 13;
            this.d += this.c[n4++];
            this.c[n3] = n = this.c[(n2 & 0x3FC) >> 2] + this.d + this.e;
            this.b[n3++] = this.e = this.c[(n >> 8 & 0x3FC) >> 2] + n2;
            n2 = this.c[n3];
            this.d ^= this.d >>> 6;
            this.d += this.c[n4++];
            this.c[n3] = n = this.c[(n2 & 0x3FC) >> 2] + this.d + this.e;
            this.b[n3++] = this.e = this.c[(n >> 8 & 0x3FC) >> 2] + n2;
            n2 = this.c[n3];
            this.d ^= this.d << 2;
            this.d += this.c[n4++];
            this.c[n3] = n = this.c[(n2 & 0x3FC) >> 2] + this.d + this.e;
            this.b[n3++] = this.e = this.c[(n >> 8 & 0x3FC) >> 2] + n2;
            n2 = this.c[n3];
            this.d ^= this.d >>> 16;
            this.d += this.c[n4++];
            this.c[n3] = n = this.c[(n2 & 0x3FC) >> 2] + this.d + this.e;
            this.b[n3++] = this.e = this.c[(n >> 8 & 0x3FC) >> 2] + n2;
        }
        n4 = 0;
        while (n4 < 128) {
            n2 = this.c[n3];
            this.d ^= this.d << 13;
            this.d += this.c[n4++];
            this.c[n3] = n = this.c[(n2 & 0x3FC) >> 2] + this.d + this.e;
            this.b[n3++] = this.e = this.c[(n >> 8 & 0x3FC) >> 2] + n2;
            n2 = this.c[n3];
            this.d ^= this.d >>> 6;
            this.d += this.c[n4++];
            this.c[n3] = n = this.c[(n2 & 0x3FC) >> 2] + this.d + this.e;
            this.b[n3++] = this.e = this.c[(n >> 8 & 0x3FC) >> 2] + n2;
            n2 = this.c[n3];
            this.d ^= this.d << 2;
            this.d += this.c[n4++];
            this.c[n3] = n = this.c[(n2 & 0x3FC) >> 2] + this.d + this.e;
            this.b[n3++] = this.e = this.c[(n >> 8 & 0x3FC) >> 2] + n2;
            n2 = this.c[n3];
            this.d ^= this.d >>> 16;
            this.d += this.c[n4++];
            this.c[n3] = n = this.c[(n2 & 0x3FC) >> 2] + this.d + this.e;
            this.b[n3++] = this.e = this.c[(n >> 8 & 0x3FC) >> 2] + n2;
        }
    }

    private void a(boolean n) {
        int n2 = -1640531527;
        int n3 = -1640531527;
        int n4 = -1640531527;
        int n5 = -1640531527;
        int n6 = -1640531527;
        int n7 = -1640531527;
        int n8 = -1640531527;
        int n9 = -1640531527;
        n = 0;
        while (n < 4) {
            n6 += (n9 ^= n8 << 11);
            n8 += n7;
            n5 += (n8 ^= n7 >>> 2);
            n7 += n6;
            n4 += (n7 ^= n6 << 8);
            n6 += n5;
            n3 += (n6 ^= n5 >>> 16);
            n5 += n4;
            n2 += (n5 ^= n4 << 10);
            n4 += n3;
            n9 += (n4 ^= n3 >>> 4);
            n3 += n2;
            n8 += (n3 ^= n2 << 8);
            n2 += n9;
            n7 += (n2 ^= n9 >>> 9);
            n9 += n8;
            ++n;
        }
        n = 0;
        while (n < 256) {
            n9 += this.b[n];
            n8 += this.b[n + 1];
            n7 += this.b[n + 2];
            n6 += this.b[n + 3];
            n5 += this.b[n + 4];
            n4 += this.b[n + 5];
            n3 += this.b[n + 6];
            n2 += this.b[n + 7];
            n6 += (n9 ^= n8 << 11);
            n8 += n7;
            n5 += (n8 ^= n7 >>> 2);
            n7 += n6;
            n4 += (n7 ^= n6 << 8);
            n6 += n5;
            n3 += (n6 ^= n5 >>> 16);
            n5 += n4;
            n2 += (n5 ^= n4 << 10);
            n4 += n3;
            n9 += (n4 ^= n3 >>> 4);
            n3 += n2;
            n8 += (n3 ^= n2 << 8);
            n2 += n9;
            n7 += (n2 ^= n9 >>> 9);
            this.c[n] = n9 += n8;
            this.c[n + 1] = n8;
            this.c[n + 2] = n7;
            this.c[n + 3] = n6;
            this.c[n + 4] = n5;
            this.c[n + 5] = n4;
            this.c[n + 6] = n3;
            this.c[n + 7] = n2;
            n += 8;
        }
        n = 0;
        while (n < 256) {
            n9 += this.c[n];
            n8 += this.c[n + 1];
            n7 += this.c[n + 2];
            n6 += this.c[n + 3];
            n5 += this.c[n + 4];
            n4 += this.c[n + 5];
            n3 += this.c[n + 6];
            n2 += this.c[n + 7];
            n6 += (n9 ^= n8 << 11);
            n8 += n7;
            n5 += (n8 ^= n7 >>> 2);
            n7 += n6;
            n4 += (n7 ^= n6 << 8);
            n6 += n5;
            n3 += (n6 ^= n5 >>> 16);
            n5 += n4;
            n2 += (n5 ^= n4 << 10);
            n4 += n3;
            n9 += (n4 ^= n3 >>> 4);
            n3 += n2;
            n8 += (n3 ^= n2 << 8);
            n2 += n9;
            n7 += (n2 ^= n9 >>> 9);
            this.c[n] = n9 += n8;
            this.c[n + 1] = n8;
            this.c[n + 2] = n7;
            this.c[n + 3] = n6;
            this.c[n + 4] = n5;
            this.c[n + 5] = n4;
            this.c[n + 6] = n3;
            this.c[n + 7] = n2;
            n += 8;
        }
        this.b();
        this.a = 256;
    }
}

