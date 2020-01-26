package com.example.alphaver;

public class Upload {

    private String mName;
    private String mImageUrl;
    private String key;

    public Upload(){

        //constructor
    }

    public Upload(String name, String imageUrl, String id){

        if (name.trim().equals("")) name = "No Name";

        this.mName = name;
        this.mImageUrl = imageUrl;
        this.key = id;
    }


    public String getName(){
        return mName;
    }

    public void setName(String name){
        mName = name;
    }

    public String getmImageUrl(){
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl){
        mImageUrl = imageUrl;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key1){
        key = key1;
    }


}
