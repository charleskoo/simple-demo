package com.example.demo.common;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * Provides an md5 and sha1 hash function without producing deprecation warnings.
 */
public class LegacyHashing {
    @SuppressWarnings("deprecation")
    public static HashFunction md5() {
        return Hashing.md5();
    }

    @SuppressWarnings("deprecation")
    public static HashFunction sha1() {
        return Hashing.sha1();
    }
}