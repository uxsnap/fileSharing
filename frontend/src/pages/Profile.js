import React, { useState, useEffect } from 'react';
import { Row, Col, Input, Icon, IconList, FileList } from '../components';

export const Profile = () => {
	const [search, setSearch] = useState("");
  const [img, setImg] = useState("");

  const iconList = [
    { icon: 'person', info: "test", label: 'test'},
    { icon: 'person', info: "test", label: 'test'},
    { icon: 'person', info: "test", label: 'test'},
    { icon: 'person', info: "test", label: 'test'},
    { icon: 'person', info: "test", label: 'test'},
  ];

  const fileItems = [
    { name: 'filename.jpg', id: 1 },
    { name: 'filename.jpg', id: 1 },
    { name: 'filename.jpg', id: 1 },
    { name: 'filename.jpg', id: 1 },
    { name: 'filename.jpg', id: 1 },
  ];

	return (
		<div className="profile">
			<Row>
        <Col>  
  				<div className="profile__header profile-header">
  					<div className="profile-header__brand">
              <Icon iconType="files" />
              <span className="profile-header__name">FILES</span>
  					</div>		
  					<div className="profile-header__search">
  						<Input 
                value={search} 
                onChange={setSearch}
                placeholder="Search for friends"
                rightIcon="loupe"
              />
  					</div>		
				  </div>
        </Col>
			</Row>
			{// <Row>
			// 	<Col>
			// 		<Stats />
			// 	</Col>
			// </Row>
      }
			<Row curClass="profile__main">
        <Col>
				  <div className="profile__me me">
          	<div>
    					<div className="me__avatar">
                {img.length
                  ? <img src={img} alt="ME"/>
                  : <Icon iconType="person" />
                }
              </div>
  					  <div className="me__info">
                <IconList items={iconList} />
              </div>
            </div>
				  </div>
        </Col>
				<div className="profile__files">
          <FileList items={fileItems} onDelete={() => false} onEdit={() => false}/>
				</div>
			</Row>
		</div>
	);	
};