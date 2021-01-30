import React from 'react';
import { Icon } from './index';

export default ({ error, children, onClose }) => {
	const { errors } = error;

	setTimeout(() => onClose(), 10000);

	return (
		<div className="error-wrapper">
      {error &&
  			<div className="error-box">
					<div className="error-box__title">Error</div>
					<div>
						{errors && (
							<ul className="error-box__list">
								{
									errors.map((error) => (
										<li>{error}</li>
									))
								}
							</ul>)
						}
					</div>
  				<Icon iconType="close" onClick={onClose}/>
  			</div>
      }
			{children}
		</div>
	);
};