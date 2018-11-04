//
//  HealthBar.h
//  PokeWatch
//
//  Created by Marc Davis on 4/6/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HealthBar : UIView {
    NSTimer *t;
    double goalP;
    BOOL up;
}
-(void)animateToPercent:(double)percent;
@property (nonatomic, readwrite) double percent;

@end
