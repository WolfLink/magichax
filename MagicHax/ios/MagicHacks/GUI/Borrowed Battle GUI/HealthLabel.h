//
//  HealthLabel.h
//  PokeWatch
//
//  Created by Marc Davis on 4/10/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HealthLabel : UILabel {
    int currentGoal;
}
-(void)animateToCurrentHealthValue:(int)value;
@property (nonatomic, readwrite) int currentValue;
@property (nonatomic, readwrite) NSString *format;
@end
