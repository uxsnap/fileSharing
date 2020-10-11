import React from 'react';
import { Icon } from './index';

export default ({ items, stub }) => (
 	<div class="icon-list">
 		<ul class="icon-list__list">
 			{items.length 
 				? items.map((item, ind) => (
 					<li class="icon-list__item">
 						<Icon iconType={item.icon} />
 						<span class="icon-list__info">{item.label}</span>
 					</li>
 				))
 				: stub ? stub : ''
 			}
 		</ul>
 	</div>
);