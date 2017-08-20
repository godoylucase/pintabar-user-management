package com.pintabar.usermanagement.service.repository.querydsl;

import com.pintabar.usermanagement.model.entity.QUser;
import com.querydsl.core.types.Predicate;

/**
 * Created by lucasgodoy on 4/06/17.
 */
public class UserPredicates {


	public static Predicate deletedUser(Boolean isDeleted) {
		if (isDeleted == null) {
			return null;
		} else if (isDeleted) {
			return QUser.user.deleted.isTrue();
		}
		return QUser.user.deleted.isFalse();
	}
}
