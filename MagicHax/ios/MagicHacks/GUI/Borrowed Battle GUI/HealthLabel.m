//
//  HealthLabel.m
//  PokeWatch
//
//  Created by Marc Davis on 4/10/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import "HealthLabel.h"

@implementation HealthLabel

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
-(void)animateToCurrentHealthValue:(int)value {
    currentGoal = value;
    [self recurse];
}
-(void)recurse {
    if (_currentValue > currentGoal) {
        _currentValue--;
        [self performSelector:@selector(recurse) withObject:nil afterDelay:0.03];
    }
    else if(_currentValue < currentGoal) {
        _currentValue++;
        [self performSelector:@selector(recurse) withObject:nil afterDelay:0.03];

    }
    [self redisplay];
}
-(void)redisplay {
    self.text = [NSString stringWithFormat:_format,_currentValue];
}
-(void)setCurrentValue:(int)currentValue {
    _currentValue = currentValue;
    [self redisplay];
}
-(void)setFormat:(NSString *)format {
    _format = format;
    [self redisplay];
}
@end
