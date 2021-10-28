package org.prgrms.cream.domain.user.exception;

import org.prgrms.cream.global.error.ErrorCode;
import org.prgrms.cream.global.error.exception.BusinessException;

public class InvalidArgumentException extends BusinessException {

	public InvalidArgumentException(ErrorCode errorCode) {
		super(errorCode);
	}
}
