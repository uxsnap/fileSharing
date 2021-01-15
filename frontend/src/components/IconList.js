import React from 'react';
import { generateKey } from '../utils';
import { Icon } from './index';

export default ({ items, stub }) => (
 	<div className="icon-list">
 		<ul className="icon-list__list">
 			{items.length 
 				? items.map((item, ind) => (
 					<li key={generateKey()} className="icon-list__item">
 						<Icon iconType={item.icon} onClick={item.onClick} />
 						{item.label ? <span className="icon-list__info">{item.label}</span> : ''}
 					</li>
 				))
 				: stub ? stub : ''
 			}
 		</ul>
 	</div>
);