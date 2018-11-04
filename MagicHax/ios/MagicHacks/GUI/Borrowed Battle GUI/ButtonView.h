//
//  ButtonView.h
//  PokeWatch
//
//  Created by Marc Davis on 4/6/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Types.h"

@interface ButtonView : UIView {
    UIColor *backgroundColor;
    UIColor *outlineColor;
}

@property (nonatomic, readonly) UILabel *text;
@property (nonatomic, readwrite) BOOL tapped;
-(void)setType:(BattleType)type;
@end
