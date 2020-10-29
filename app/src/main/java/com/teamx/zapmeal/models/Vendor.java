package com.teamx.zapmeal.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class Vendor implements Parcelable {
	private String product_name;
	private String product_description;
	private String creator;
	private String avatar;
	private String product_id;
	private @ServerTimestamp Date time_created;

	public Vendor(String shop_name, String shop_description, String creator, Date time_created, String avatar, String shop_id) {
		this.product_name = shop_name;
		this.product_description = shop_description;
		this.creator = creator;
		this.time_created = time_created;
		this.avatar = avatar;
		this.product_id = shop_id;
	}

	public Vendor(){

	}

	protected Vendor(Parcel in) {
		product_name = in.readString();
		product_description = in.readString();
		creator = in.readString();
		avatar = in.readString();
		product_id = in.readString();
		time_created = (Date) in.readSerializable();

	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getShop_description() {
		return product_description;
	}

	public void setShop_description(String shop_description) {
		this.product_description = shop_description;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getTime_created() {
		return time_created;
	}

	public void setTime_created(Date time_created) {
		this.time_created = time_created;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public static final Creator<Vendor> CREATOR = new Creator<Vendor>() {
		@Override
		public Vendor createFromParcel(Parcel in) {
			return new Vendor(in);
		}

		@Override
		public Vendor[] newArray(int size) {
			return new Vendor[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flag) {
		parcel.writeString(product_name);
		parcel.writeString(product_description);
		parcel.writeString(creator);
		parcel.writeString(avatar);
		parcel.writeString(product_id);
		parcel.writeSerializable(time_created);
	}
}
