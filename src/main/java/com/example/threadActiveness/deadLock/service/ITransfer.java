package com.example.threadActiveness.deadLock.service;


import com.example.threadActiveness.deadLock.UserAccount;

/**
 *@author
 *
 *类说明：银行转账动作接口
 */
public interface ITransfer {
    void transfer(UserAccount from, UserAccount to, int amount)
    		throws InterruptedException;
}
