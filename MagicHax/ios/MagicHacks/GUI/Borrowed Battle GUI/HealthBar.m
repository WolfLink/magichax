//
//  HealthBar.m
//  PokeWatch
//
//  Created by Marc Davis on 4/6/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import "HealthBar.h"

@implementation HealthBar

-(id)init {
    self = [super init];
    if (self) {
        _percent = 1;
        goalP = 1;
    }
    return self;
}
-(id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    if (self) {
        _percent = 1;
        goalP = 1;
    }
    return self;
}
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextClearRect(context, rect);
    CGContextSetRGBFillColor(context, 0.5, 0.5, 0.5, 0.5);
    CGContextFillRect(context, rect);
    
    double math = _percent * (1-_percent);
    CGContextSetRGBFillColor(context, 1-_percent+math, _percent+math, 0.1, 1);
    CGRect newRect = CGRectMake(rect.size.height/4+rect.origin.x, rect.size.height/4+rect.origin.y, (rect.size.width-rect.size.height/2)*_percent, rect.size.height/2);
    CGContextFillRect(context, newRect);
}
-(void)animateToPercent:(double)percent {
    goalP = percent;
    if (goalP > _percent) {
        up = true;
    }
    else {
        up = false;
    }
    if (t == nil) {
        t = [NSTimer scheduledTimerWithTimeInterval:0.0166 target:self selector:@selector(performAnimation) userInfo:nil repeats:YES];
    }
    [t fire];
}
-(void)performAnimation {
    if ((_percent > goalP && up) || (_percent < goalP && !up)) {
        _percent = goalP;
        [t invalidate];
        t = nil;
    }
    else if(up){
        _percent += 0.005;
    }
    else {
        _percent -= 0.005;
    }
    [self setNeedsDisplay];
}
-(void)testAnimation{
    [self animateToPercent:0];
}

@end
