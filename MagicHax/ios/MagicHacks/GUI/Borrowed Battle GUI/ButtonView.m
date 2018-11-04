//
//  ButtonView.m
//  PokeWatch
//
//  Created by Marc Davis on 4/6/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import "ButtonView.h"

@implementation ButtonView

-(id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    self.backgroundColor = [UIColor clearColor];
    if (self) {
        _text = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, frame.size.width, frame.size.height)];
        _text.textAlignment = NSTextAlignmentCenter;
        _text.font = [UIFont systemFontOfSize:20];
        //[self setType:kBattleTypeNull];
        [self addSubview:_text];
        [self bringSubviewToFront:_text];
    }
    return self;
}
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
    CGContextRef context = UIGraphicsGetCurrentContext();
    [backgroundColor setFill];
    [outlineColor setStroke];
    CGPathRef roundRect = CGPathCreateWithRoundedRect(CGRectMake(rect.origin.x + 4, rect.origin.y + 4, rect.size.width-8, rect.size.height-8), 15, 15, NULL);
    CGContextAddPath(context, roundRect);
    CGContextSetLineWidth(context, 2);
    CGContextDrawPath(context, kCGPathStroke);
    
    if (_tapped) {
        [[UIColor colorWithRed:0.5 green:0.5 blue:0.5 alpha:0.5] setFill];
        CGContextAddPath(context, roundRect);
        CGContextFillPath(context);
    }
}
-(void)setType:(BattleType)type {
    switch (type) {
        case kBattleTypeAir :
            backgroundColor = [UIColor colorWithRed:0.09 green:0.78 blue:0.81 alpha:1];
            outlineColor = [UIColor colorWithRed:0.015 green:0.34 blue:0.42 alpha:1];
            break;
        case kBattleTypeElectric:
            backgroundColor = [UIColor colorWithRed:0.98 green:0.91 blue:0.11 alpha:1];
            outlineColor = [UIColor colorWithRed:0.22 green:0.67 blue:0.42 alpha:1];
            break;
        case kBattleTypeFire:
            backgroundColor = [UIColor colorWithRed:0.76 green:0.54 blue:0.05 alpha:1];
            outlineColor = [UIColor colorWithRed:0.52 green:0.11 blue:0.02 alpha:1];
            break;
        case kBattleTypePlant:
            backgroundColor = [UIColor greenColor];
            outlineColor = [UIColor colorWithRed:0.1 green:0.5 blue:0.1 alpha:1];
            break;
        case kBattleTypeWater:
            backgroundColor = [UIColor blueColor];
            outlineColor = [UIColor colorWithRed:0.1 green:0.1 blue:0.5 alpha:1];
            break;
        case kBattleTypeEarth:
            backgroundColor = [UIColor brownColor];
            outlineColor = [UIColor colorWithRed:0.4 green:0.3 blue:0.1 alpha:1];
            break;
        //more cases for all types in here
        default:
            backgroundColor = [UIColor lightGrayColor];
            outlineColor = [UIColor darkGrayColor];
            break;
    }
    //_text.textColor = outlineColor;
    _text.textColor = backgroundColor;
    [self setNeedsDisplay];
}
-(void)setTapped:(BOOL)tapped {
    _tapped = tapped;
    [self setNeedsDisplay];
}
@end
