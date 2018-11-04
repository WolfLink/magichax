//
//  MessageView.m
//  PokeWatch
//
//  Created by Marc Davis on 4/10/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import "MessageView.h"

@implementation MessageView
-(id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    if (self) {
        myLabel = [[UILabel alloc] initWithFrame:CGRectMake(2, 0, frame.size.width, frame.size.height)];
        myLabel.textColor = [UIColor whiteColor];
        myLabel.textAlignment = NSTextAlignmentLeft;
        [self addSubview:myLabel];
        nowPlaying = false;
    }
    return self;
}
-(void)displayMessages {
    if (!nowPlaying) {
        nowPlaying = true;
        [self messageShow];
    }
}
-(void)messageShow {
    if ([BattleMessenger isEmpty]) {
        nowPlaying = false;
        return;
    }
    myLabel.text = [BattleMessenger getNextMessage];
    myLabel.frame = CGRectMake(self.frame.size.width, 0, self.frame.size.width, self.frame.size.height);
    [UIView animateWithDuration:[BattleMessenger getDelay]*0.25 animations:^{
        myLabel.frame = CGRectMake(2, 0, self.frame.size.width, self.frame.size.height);
    }completion:^(BOOL finished){
        [self performSelector:@selector(messageHide) withObject:nil afterDelay:[BattleMessenger getDelay]];
    }];
}
-(void)messageHide {
    [UIView animateWithDuration:[BattleMessenger getDelay]*0.25 animations:^{
        myLabel.frame = CGRectMake(-self.frame.size.width, 0, self.frame.size.width, self.frame.size.height);
    }completion:^(BOOL finished){
        [self messageShow];
    }];
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

@end
