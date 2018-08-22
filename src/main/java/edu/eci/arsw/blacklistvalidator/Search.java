/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author 2109734
 */
public class Search extends Thread{
    
    private final int start;
    private final int end;
    private final String host;
    private LinkedList<Integer> blackList = new LinkedList<>();
    private final AtomicInteger ocurrencesCount;
    private int checkedListsCount;
    
    private HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
    
    public Search(int start,int end,String host, AtomicInteger ocurrencesCount){
        this.start = start;
        this.end = end;
        this.host = host;
        this.ocurrencesCount = ocurrencesCount;
        checkedListsCount = 0;        
    }
    
    @Override
    public void run(){
        int i=start;
        while (i<start+end && ocurrencesCount.get()<5){
            checkedListsCount++;
            if (skds.isInBlackListServer(i, host)){
                blackList.add(i);
                ocurrencesCount.getAndIncrement();
            }
            i++;
        }
    }
    
    public int getCheckedListCount(){
        return checkedListsCount;
    }
    
    public LinkedList<Integer> getBlackList(){
        return blackList;
    }   
}